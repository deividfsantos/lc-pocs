package com.dsantos.observer;

public class ObserverPatternTest {

    public static void main(String[] args) {
        Topic topic = new Topic();

        Observer obj1 = new MyTopicSubscriber("Obj1");
        Observer obj2 = new MyTopicSubscriber("Obj2");
        Observer obj3 = new MyTopicSubscriber("Obj3");

        topic.register(obj1);
        topic.register(obj2);
        topic.register(obj3);

        obj1.setSubject(topic);
        obj2.setSubject(topic);
        obj3.setSubject(topic);

        obj1.update();

        topic.postMessage("New Message");
    }

}
