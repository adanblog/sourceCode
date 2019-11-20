package com.shan.test;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	App app=new App();
    	app.production2();
    }
    
    public void production1() {
    	TelevisionService small=new SmallTelevisionServiceImp();
    	small.production();
    	TelevisionService midsize=new MidsizeTelevisionServiceImp();
    	midsize.production();
    	TelevisionService large=new LargeTelevisionServiceImpl();
    	large.production();
    }
    
    public void production2() {
        ServiceLoader<TelevisionService> loadedImpl = ServiceLoader.load(TelevisionService.class);
		Iterator<TelevisionService> it = loadedImpl.iterator();
		while (it.hasNext()) {
			TelevisionService service = it.next();
			service.production();
		}
    }
}
