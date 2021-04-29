# in-the-wind

## Purpose
Proof of concept for securing internal data; maybe financial or medical records.

## Overview
All the ideas are simple; but put together appear to provide some interesting, and potentially useful security functionality. 

At the root of the security mechanism, this approach uses a one-time-pad.  Of course, this has the benefit of being unbreakable.  It has the downside that the key is as big as the message.

In this method, there is no notion of key and cipher; the encoding factors are commutative and are functionaly interchangable.

Since this solution is aimed at securing internal data, one probably expects that all of the factors are kept apart in such a way that it is unlikely that all the factors could be stolen.  

Of course, additional layers of security could be added.

## Step One
First part is a proof of concept.  

In this POC, a text message is "unwound" into several (at least two) text factors.  To retrieve the message, all the factors are combined.  Without all the factors, the message cannot be retrieved.  The factors themselves are commutative.  There is no notion of distinct keys and ciphers.

There are several issues in the POC to call out, that would need to be addressed before applying this code to production.
1. The POC has only implemented support for text messages and text factors.  Surely other data types will be of interest.
1. The POC leverages default Java randomization; which is not true randomness expected for one-time-pads, and may not be sufficiently strong for some applications
1. The POC has not addressed issues of serialization and speed.

### Functionality
The Winder interface exposes two methods: one of which creates factors from a message; and the other which combines factors - to retrieve the original method.
However, these two methods can be leveraged in multiple ways to achieve some interesting behaviors.
1. A message can be unwound into any number of factors greater than one.
1. Factors can be recombined in any order (This is not implicit in the interface, but the strategy in the POC implementation should apply generally).
1. Compromised factors can be replaced without jeopardizing security.
1. If needed, existing factors can be expanded.  For example, say that a message has been secured with two factors, and a need arises to have a third factor.
1. If needed, existing factors can be reduced.  As in the example above, we can also go from having three factors to having two.
1. If needed, we can have parallel sets of factors, each of which can restore the message.



### Getting started
1. Perhaps the best way to get started is to use a debugger and step through some of the unit tests.

## Potential next steps
1. "True" randomization
1. Expanded support for more data types.
1. **Serialization support** (configurable) - there would need to be careful thought on how to separate the factors in such a way that a security breach is unlikely to compromize all of them.
1. More tests
1. Development of a suite of microservices.




