package dev.anurag.JournalApp.service;

import dev.anurag.JournalApp.entity.Users;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(Users.builder().userName("ram").password("ram").build()),
                Arguments.of(Users.builder().userName("shyam").password("").build())
        );

    }
}
