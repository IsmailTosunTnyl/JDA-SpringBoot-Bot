package net.ismailtosun.discordbotultimate.Listeners;

import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class MessageCreateListener implements EventListener {
    @Override
    public void onEvent(GenericEvent genericEvent) {
        System.out.println("Received event: " + genericEvent.getClass().getSimpleName());

        if (genericEvent instanceof MessageReceivedEvent) {
            MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) genericEvent;
            System.out.println("Message received: " + messageReceivedEvent.getMessage().getContentDisplay());
        }


    }


}
