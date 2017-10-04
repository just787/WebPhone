package com.wdl.web.frame;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化配置文件定义的处理器
 */
public class Initor {
    // 配置文件路径
    private static String path = "config.xml";
    // 初始化类的唯一对象
    public static Initor initor = new Initor();
    // 访问路径对应的处理器映射map
    private Map<String, HandlerElement> handlerElementMap = new HashMap<String, HandlerElement>();

    /**
     * 初始化所有处理器
     */
    public void init() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            Document document = documentBuilderFactory.newDocumentBuilder().parse(path);
            documentMap(document);
            annotationMap();

            System.out.println("初始化注入完成!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("初始化注入失败!");
        }
    }

    /**
     * 获取配置文件定义到方法缓存到Map
     *
     * @param document
     */
    private void documentMap(Document document) {
        NodeList nodeList = document.getElementsByTagName("action");
        for (int i = 0, len = nodeList.getLength(); i < len; i++) {
            Element element = (Element) nodeList.item(i);
            // 访问路径
            String pathStr = element.getAttribute("name").replaceAll("\\s*|\t|\r|\n", "");
            // 类全路径
            String classNameStr = element.getElementsByTagName("handleClass").item(0).getFirstChild().getNodeValue()
                    .replaceAll("\\s*|\t|\r|\n", "");
            // 路径对应的方法名
            String methodStr = element.getElementsByTagName("handleMethod").item(0).getFirstChild().getNodeValue()
                    .replaceAll("\\s*|\t|\r|\n", "");

            if (handlerElementMap.containsKey(pathStr)) {
                continue;
            }

            HandlerElement handlerElement = getHandlerElement(classNameStr, methodStr);
            if (handlerElement != null) {
                handlerElementMap.put(pathStr, handlerElement);
            }
        }
    }


    private void annotationMap() {
        // 通过扫描获取包下都所有类
        List<Class<?>> clazzs = ScanPackageClass.getClasses("com.wdl.web.service");
        try {
            if (clazzs != null) {
                for (Class<?> clazz : clazzs) {
                    for (Method method : clazz.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            String path = method.getAnnotation(RequestMapping.class).value().replaceAll("/", "");
                            if (handlerElementMap.containsKey(path)) {
                                continue;
                            }
                            method.setAccessible(true);
                            handlerElementMap.put(path, new HandlerElement(clazz.newInstance(), method));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("通过注解注入失败");
        }
    }

    /**
     * 反射获取类对象和方法
     *
     * @param classNameStr
     * @param methodStr
     * @return
     */
    private HandlerElement getHandlerElement(String classNameStr, String methodStr) {

        HandlerElement handlerElement = null;
        try {
            Class<?> clazz = Class.forName(classNameStr);
            if (clazz != null) {
                Object instance = clazz.newInstance();

                // 注: 方法只允许有两个参数
                Method method = clazz.getDeclaredMethod(methodStr, HttpServletRequest.class, HttpServletResponse.class);
                method.setAccessible(true);
                handlerElement = new HandlerElement(instance, method);
            }
        } catch (Exception e) {
            System.out.println("通过配置文件反射获取类对象和方法失败!");
            throw new ServerException(-1);
        }

        return handlerElement;
    }

    /**
     * 获取处理器
     *
     * @param path
     * @return
     */
    public HandlerElement getHandlerElement(String path) {
        return handlerElementMap.get(path);
    }

}
