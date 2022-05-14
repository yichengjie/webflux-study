####  MethodHandler的使用
1. 编写实体父类
```java
public class MHSuper {
    public static int y(Boolean a){
        System.out.println("super:: static");
        return 1 ;
    }
    public static int z(MHSuper a){
        System.out.println(a.getClass());
        return 1 ;
    }
    public int x(boolean a){
        System.out.println("super::primitive");
        return 1 ;
    }
    public int x(Boolean a){
        System.out.println("super::boxed");
        return 1 ;
    }
}
```
2. 编写实体子类
```java
public class MHSub extends MHSuper{
    public static int y(Boolean a){
        System.out.println("sub::static");
        return 1 ;
    }
    public int x(boolean a){
        System.out.println("sub::primitive");
        return 1 ;
    }
    public int x(Boolean a){
        System.out.println("sub::boxed");
        return 1 ;
    }
}
```
3. 编写单元测试
```java
public class MethodHandlerTest {
    //https://my.oschina.net/u/3847203/blog/2221569
    @Test
    public void findVirtual() throws Throwable {
        Object a ;
        MethodType mt = MethodType.methodType(int.class, Boolean.class) ;
        MethodHandle mh = MethodHandles.lookup().findVirtual(MHSuper.class, "x", mt);
        //mh.bindTo(null).invoke(false) ; // NullPointerException
        //mh.invoke(false) ; //WrongMethodTypeException: cannot convert MethodHandle(MHSuper,Boolean)int to (boolean)void
        //mh.bindTo(new Object()).invoke(false) ; //Cannot cast java.lang.Object to com.yicj.func.handler.MHSuper
        //mh.bindTo(new MHSuper()).invoke(false); //super::boxed
        //mh.bindTo(new MHSuper()).invoke(Boolean.FALSE) ; //super::boxed
        ////////////////////////////////////////////////////////////////////
        //a = (int)mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //super::boxed
        //a = (Number)mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //WrongMethodTypeException: expected (Boolean)int but found (Boolean)Number
        //a = (Integer)mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //WrongMethodTypeException: expected (Boolean)int but found (Boolean)Integer
        //mh.bindTo(new MHSuper()).invokeExact(Boolean.FALSE) ; //expected (Boolean)int but found (Boolean)void
        ////////////////////////////////////////////////////////////////////
        //mh.bindTo(new MHSub()).invoke(false) ; //sub::boxed
        //mh.bindTo(new MHSub()).invoke(Boolean.FALSE) ;//sub::boxed
        //a = (int)mh.bindTo(new MHSub()).invokeExact(Boolean.FALSE) ; //sub::boxed
        ////////////////////////////////////////////////////////////////////
    }
    @Test
    public void findStatic() throws Throwable{
        Object a ;
        MethodType mt = MethodType.methodType(int.class, Boolean.class) ;
        MethodHandle mh = MethodHandles.lookup().findStatic(MHSuper.class, "y", mt) ;
        // mh.invoke(false) ; //super:: static
        //a = (int) mh.invokeExact(Boolean.FALSE) ; //super:: static
        ////////////////////////////////////////////////////////////////////
        mh = MethodHandles.lookup().findStatic(MHSuper.class, "z", MethodType.methodType(int.class, MHSuper.class)) ;
        MHSuper sup = new MHSub() ;
        a = (int)mh.invokeExact(sup) ; //class com.yicj.func.handler.MHSub

        MHSub sub = new MHSub() ;
        a = (int)mh.invokeExact(sub) ; //WrongMethodTypeException: expected (MHSuper)int but found (MHSub)int

    }
}
```