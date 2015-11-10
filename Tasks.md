# Introduction #

When designing software it is important to be able to specify issues that relate to the project better. Using simply the default ones, while may be sufficient, is far from an ideal, and I have hence created some custom labels to use with issues.


# Details of additions #

**_Additions:_**
| **Status** | **Types** | **Components** |
|:-----------|:----------|:---------------|
| Awaiting   | Development | FSM, FuSM      |


_Awaiting_ is intended to indicate that work on that issue has been suspended because it requires another component to work. Also you can use it to indicate that you are waiting for work to complete on the algorithm before continuing. Usually you might add such an issue to the 'blocked on' field but this is better for a small project.

_Development_ This is to signify a development task which is not already a pre-existing feature. If you have something to add then you can use the Development task template when add the issue.

_FSM_ If the issue relates to the finite state machine then add this label

_FuSM_ If the issue relates to the fuzzy state machine then add this label

> 
---


## Using issue reports ##

Google explains this process well...

> The expected issue life-cycle for most projects is something like this:
    1. A user reports an issue and it has status _New_.
    1. The issue is triaged by a project member, if it was not originally entered and triaged at the same time. It may be rejected by setting its status to _Invalid_, _WontFix_, or _Duplicate_. Or, it may be _Accepted_. If it is accepted, it may be labelled with a milestone and a priority within that milestone, and it may also be labelled to identify the part of the product affected or the nature of the cause of the problem.
    1. The project owner may ask for more information and the initial reporter may add comments to provide that information. The issue owner may set the status to _Started_.
    1. Other users may provide additional comments that can help resolve the issue, or express their interest in having the issue resolved in a timely milestone.
    1. If the original owner cannot work on the issue, or discovers that another project member would be a better person to work on the issue, it can be reassigned to another owner, or to no owner. Likewise, if work on the issue cannot be fit into a given milestone, the issue can be slipped by labelling it with a later milestone, or the milestone label can be removed altogether.
    1. Once the development work has been done to resolve the issue, it's status can be set to _Fixed_. If the team wishes to track which changes have been made but not yet incorporated into a release, they may decide to set the status to something like _FixPending_.
    1. Teams that are disciplined about quality will ask the issue reporter or another team member to verify that the fix actually fixed the reported problem, and set the status to _Verified_ once that has been done.
    1. If verification fails, or if the original reporter adds a comment to say that the Fixed issue is still a problem, the issue can be set back to an open status.