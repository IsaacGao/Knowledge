package com.isaac_gao.netty.handler_chain;

/**
 * 这是一个 pipeline
 * pipeline > 持有 context > context 持有 handler
 */
public class GzyPipeline {

    private HandlerChainContext head = new HandlerChainContext(HandlerChainContext::runNext);

    public void addLast(AbsHandler handler) {
        HandlerChainContext cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new HandlerChainContext(handler);
    }

    public void process(Object args){
        head.doHandler(args);
    }

    public static void main(String[] args) {
        //如果需要开始运行，则要找到头
        GzyPipeline pipeline = new GzyPipeline();
        pipeline.addLast(new EatHandler());
        pipeline.addLast(new DrinkHandler());
        pipeline.addLast(new DrinkHandler());
        pipeline.addLast(new EatHandler());

        pipeline.process("original.");
    }

}

class HandlerChainContext {

    private AbsHandler handler;
    public HandlerChainContext next;

    public HandlerChainContext(AbsHandler handler) {
        this.handler = handler;
    }

    public void doHandler(Object args) {
        handler.handler(this, args);
    }

    public void runNext(Object args) {
        if(next == null){
            return;
        }
        next.doHandler(args);
    }


}

interface AbsHandler {
    void handler(HandlerChainContext cxt, Object in);
}

class EatHandler implements AbsHandler {

    @Override
    public void handler(HandlerChainContext cxt, Object in) {
        in = in + "eat.";
        System.out.println(in);
        cxt.runNext(in);
    }
}

class DrinkHandler implements AbsHandler {

    @Override
    public void handler(HandlerChainContext cxt, Object in) {
        in = in + "drink.";
        System.out.println(in);
        cxt.runNext(in);
    }
}
