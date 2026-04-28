package com.myproject.furnitureshop.transactions.event;

import java.util.Set;

public record RoleInvalidationEvent(
        Set<String> roles) { }
