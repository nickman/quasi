/*
 * Quasar: lightweight threads and actors for the JVM.
 * Copyright (C) 2013, Parallel Universe Software Co. All rights reserved.
 * 
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *  
 *   or (per the licensee's choosing)
 *  
 * under the terms of the GNU Lesser General Public License version 3.0
 * as published by the Free Software Foundation.
 */
package co.paralleluniverse.actors;

import static co.paralleluniverse.actors.ActorRefDelegateImpl.stripDelegates;
import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Static utility methods for working with actors.
 *
 * @author pron
 */
public final class ActorUtil {
    /**
     * Generates a random identifier object (with correct {@code equals} and {@code hashCode} semantics) that is unlikely to be equal
     * to any other object generated by this method. This object can be used as a good unique actor message identifier.
     *
     * @return a newly allocated random identifier object
     */
    public static Object randtag() {
        return new BigInteger(80, ThreadLocalRandom.current()) {
            @Override
            public String toString() {
                return toString(16);
            }
        };
    }

    /**
     * Attempts to send a message to an actor, and if the message cannot be sent due to a full mailbox - interrupt the actor.
     * This is useful for sending a shutdown message to possibly malfunctioning actors.
     *
     * @param actor   the actor to which we send the message
     * @param message the message
     */
    public static void sendOrInterrupt(ActorRef actor, Object message) {
        ((ActorRefImpl) stripDelegates(actor)).sendOrInterrupt(message);
    }

    public static boolean equals(ActorRef<?> ref1, ActorRef<?> ref2) {
        return Objects.equals(stripDelegates(ref1), stripDelegates(ref2));
    }

    private ActorUtil() {
    }
}