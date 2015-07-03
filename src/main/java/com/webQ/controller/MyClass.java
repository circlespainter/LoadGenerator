package com.webQ.controller;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import com.webQ.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class MyClass {

  private final Logger logger = LoggerFactory.getLogger(MyClass.class);
  private final HelloWorldService helloWorldService;

  @Autowired
  public MyClass(HelloWorldService helloWorldService) {
    this.helloWorldService = helloWorldService;
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index(Map<String, Object> model) {
    logger.debug("index() is executed!");
    model.put("title", helloWorldService.getTitle(""));
    model.put("msg", helloWorldService.getDesc());
    return "index";
  }

  @RequestMapping(value = "/hello/{name:.+}", method = RequestMethod.GET)
  public ModelAndView hello(@PathVariable("name") String name) throws SuspendExecution, InterruptedException {
    Fiber.sleep(10);
    logger.debug("hello() is executed - $name {}", name);
    ModelAndView model = new ModelAndView();
    model.setViewName("index");
    model.addObject("title", helloWorldService.getTitle(name));
    model.addObject("msg", helloWorldService.getDesc());
    return model;
  }

  @RequestMapping(value = "/fiber", method = RequestMethod.GET)
  public String hello() throws SuspendExecution, InterruptedException {
    System.out.println("doneeeeeeeeeeeeeeeeeeeeeee");
    for (int i = 0; i < 10; ++i) {
      Fiber<Void> f1 = new Fiber<Void>(() -> {

        Fiber.sleep(10);
        System.out.println(Fiber.currentFiber().getName() + "done");

      }).start();
    }
    /*//HttpGet request = new HttpGet("http://10.129.26.133:8000/proxy1?limit=515000");
		HttpGet request = new HttpGet("http://www.google.com");
		for(int i=0;i<10;++i){
		Fiber<Void> f1 = new Fiber<Void>(() -> {
			
			try {
				String url = "http://www.google.com/search?q=httpClient";
			 
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet req = new HttpGet(url);
		 
			// add request header
			request.addHeader("User-Agent", USER_AGENT);
			HttpResponse response;
			
				response = client.execute(req);
			
			System.out.println("Response Code : " 
		             + response.getStatusLine().getStatusCode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			try {
				URL url = new URL("http://crunchify.com/");
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				String strTemp = "";
				while (null != (strTemp = br.readLine())) {
				System.out.println(strTemp);
				}
				} catch (Exception ex) {
				ex.printStackTrace();
				}
			try{
				 final CloseableHttpAsyncClient client =FiberCloseableHttpAsyncClient.wrap(HttpAsyncClients.custom().
							setMaxConnPerRoute(1).setMaxConnTotal(1).build());
					client.start();
			Future<HttpResponse> future = client.execute(request, null);
			 HttpResponse response1 = future.get();
			    System.out.println(request.getRequestLine() + "->" + response1.getStatusLine());
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
			  }).start();
		}*/
    return "hello";
  }

}
