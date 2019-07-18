import Annotation.XmlAttr;
import Annotation.XmlAttrImplicit;
import entity.RspPerson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.StringReader;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class XmlReader {

    public XmlReader() {
    }

    /**
     *  映射xml到实体
     * @param clazz
     * @param xmlStr
     * @param <T>
     * @return
     */
    public <T> T parseToEntity(Class<T> clazz, String xmlStr) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("GBK");
        Document doc;
        T obj = null;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
            doc = reader.read(new StringReader(xmlStr));
            Element root = doc.getRootElement();
            handleNode(root, obj);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     *  处理节点
     * @param element
     * @param t
     * @param <T>
     */
    public  <T> void handleNode(Element element, T t) {
        for (Iterator<Element> it = element.elementIterator(); it.hasNext();) {
            Element child = it.next();
            Field field = null;
            try {
                field = getFieldByAttrName(child.getName(), t);
                if (field == null) {
                    return ;
                }
                PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), t.getClass());
                Class<?> clazz = Class.forName(field.getType().getTypeName());
                Object childObject = null;
                if (descriptor.getPropertyType().getTypeName().equals("java.util.List")) {
                    childObject = Proxy.newProxyInstance(RspPerson.class.getClassLoader(), new Class[] {clazz}, new MyHandler());
                } else {
                    childObject = Class.forName(field.getType().getTypeName()).getDeclaredConstructor().newInstance();
                }
                Method setMethod = descriptor.getWriteMethod(); //获得set方法
                Type type = field.getGenericType();
                if (type instanceof ParameterizedType) {
                    //带参数类型的属性
                    ParameterizedType pt = (ParameterizedType)type; //获取参数类型
//                    System.out.println("带参数" + type.getTypeName());
                    String paramClassName = pt.getActualTypeArguments()[0].getTypeName(); //获取实体属性的参数类型，如List<A>的A
                    if (childObject instanceof List) {
                        ArrayList childList = new ArrayList();
                        Iterator<Element> childIterator = child.elementIterator();
                        while (childIterator.hasNext()) {
                            Element sonOfElement = childIterator.next();
                            System.out.println(sonOfElement.getName());
                            Object paramObject = Class.forName(paramClassName).getDeclaredConstructor().newInstance();
                            childList.add(paramObject);
                            handleNode(sonOfElement, paramObject);
                        }
                        setMethod.invoke(t, childList);
                    } else if (childObject instanceof HashMap){
                        //HashMap类型属性...
                    }
                } else {
                    //不带参数类型的属性
//                    System.out.println("非带参数" + type.getTypeName());
                    setMethod.invoke(t, child.getData());
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
        }
    }

    private <T> Field getFieldByAttrName(String xmlAttrName, T t) {
        for (Field f : t.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(XmlAttr.class)) {
                if(f.getAnnotation(XmlAttr.class).value().equals(xmlAttrName)){
                    return f;
                }
            } else if (f.isAnnotationPresent(XmlAttrImplicit.class)) {
                if (f.getAnnotation(XmlAttrImplicit.class).value().equals(xmlAttrName)){
                    return f;
                }
            }
        }
        return null;
    }
}
