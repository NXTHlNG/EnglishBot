package ru.nxthing.command;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CommandRegistryConfig {
    @Bean
    public Map<String, BotCommand> commandRegistryMap(List<AbstarctBotCommand> abstarctBotCommands) {
        Map<String, BotCommand> map = new HashMap<>();

        for (BotCommand command : abstarctBotCommands) {
            map.put(command.getCommandIdentifier(), command);
        }

        return map;
    }
}
