package org.zion.Zion.event;

import java.time.Instant;

public record DepositMadeEvent(String accountId, double amount, Instant timestamp) {

}
