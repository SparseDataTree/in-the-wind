# in-the-wind

## Purpose
Securing Data With Zero Trust
## Overview
We present a method to secure data such that:
1. No single entity has access to secured data (data privacy).
1. No single entity can block or filter access to secured data (data tyranny).
1. Trust can be dynamically granted or revoked.

At the root of the security mechanism, an algorithm produces interchangeable (distributed) encoding factors, each owned by a different entity.  (In this POC, we use a pseudo-one-time-pad to generate interchangeable entities.)
## Usage
In this POC, a text message is "unwound" into several (at least two) encoding factors.  To retrieve the message, all the encoding factors are "wound" (combined).  Without all the factors, the message cannot be retrieved. 
### Example
Say that a hospital wants to secure patient's medical records, in a way that puts zero trust in any one particular data-storage provider.  It works with a data-storage broker who arranges for multiple data-storage providers to each store a single encoding factor.  

One pattern that would protect data privacy and against data tyranny (assuming no collusion between data-providing entities) would entail the creation of two parallel sets of two encoding factors, each managed by a separate data provider, call them **Aa**, **Bb**, **Cc**, **Dd**.
**Aa** and **Bb** each own encoding factors that provide a sufficient basis to retrieve the data.  **Cc** and **Dd** each own independent encoding factors that together form a basis to retrieve the data.
Thus, none of the data-providers has access to the data; and none of the data-providers can hold the data for ransom.

Furthermore, if one of the data-providers was found to be untrustworthy, its encoding factor could be invalidated; and a new entity **Ee** could be brought into the process.

![](images/inthewind.png)




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
1. "True" randomization - The POC leverages default Java randomization; which is not true randomness expected for one-time-pads, and may not be sufficiently strong for some applications
1. Expanded support for more data types.
1. Consider that the "Winder" functionality should belong to the owner of the data; so that no "Winder" service itself becomes a data tyrant.

## Literature
Shamir, Adi. *How to Share a Secret*, 
Massachusetts Institute of Technology
Communications November 1979 of Volume 22 the ACM Number 11 pp. 612-613

