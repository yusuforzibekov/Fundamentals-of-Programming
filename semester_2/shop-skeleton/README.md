# Shop (cashboxes)

The purpose of this exercise is to train you to work with queues.


Duration: **1.5 hours**


## Description 

A shop has **N** cashboxes. Every cashbox is numbered from 0 to N - 1 and can be in one of the following states, which are described by the `CashBox.State` enum:  
* `ENABLED` - An enabled cashbox can have a nonempty queue of buyers and serve buyers. New buyers can be added to the end of its queue.  
* `IS_CLOSING` - A cashbox is in the process of closing. It serves existing buyers, but no new buyers can be added to the end of the queue.  
* `DISABLED` - A cashbox has an empty queue and cannot serve any buyer.  


When the `tact` method of class `Shop` is invoked:  
* Every nonempty queue is decreased by one buyer (which is removed from the head of a queue).  
* All queues are balanced by the count of buyers if necessary.  
* A cashbox with a state of `IS_CLOSING` changes its status to `DISABLED` if the last buyer was just served (i.e., the size of its queue is changed to 0). 

If a cashbox changes its status from  `DISABLED` to `ENABLED`, the buyers from the ends of the queues of the other cashboxes go to the end of this cashbox's queue.  

The length of queues cannot differ by more than 1, but a queue with a state of `IS_CLOSING` can have fewer buyers than other queues.  

> A cashbox with a lower number must not have fewer buyers in its queue than a cashbox with a larger number if both are in the `ENABLED` state.  

Now, please proceed to the `CashBox` class and implement the following methods:  

* `public Deque<buyer> getQueue()`  
   Returns a copy of the queue  

* `public Buyer serveBuyer()`  
   Serves a buyer if a queue is nonempty and changes state from `IS_CLOSING` to `DISABLE` if necessary  

* `public boolean inState(State state)`  
   Checks to see if a cashbox is in a specified state  

* `public boolean notInState(State state)` 
   Checks to see if a cashbox is not in a specified state  

* `public void addLast(Buyer buyer)`    
   Adds a buyer to the end of the cashbox queue  

* `public Buyer removeLast()`  
   Removes a buyer from the end of the cashbox queue and returns them

* `public String toString()`  
   Generates a textual representation of the cashbox queue (see the example below)

* `setter/getter `
   Sets/returns the state of the cashbox
  

Now, please proceed to the `Shop` class and implement the following methods:  

* `public void print()`  
   Prints all cashboxes in increasing order by number (see the example below)

* `public void addBuyer(Buyer buyer)`  
   Adds a buyer to the end of the cashbox with the smallest queue

* `public CashBox getCashBox(int cboxNumber)`  
   Returns a cashbox by its number

* `public void setCashBoxState(int cboxNumber, State state)`  
   Sets a new state for a cashbox with a specified number

* `public void tact()`  
   Does one tact (see the description above)
 

> You can add your own methods if you want. Just don't change any content in the `Buyer` class.

### Details:

A description of the `balance()` method of the `Shop` class, which redistributes buyers in cashbox queues when some of their statuses change from `DISABLED` to `ENABLED`:  
* Calculate the minimum and maximum values of the size of queues after redistribution  
* Create a new queue of defector-buyers who leave their queues  
* Iterate over the queues of all cashboxes in ascending order by number, and do the following for each:  
   * Calculate how many buyers must leave the queue—for example, _k_ buyers (_k_ ≥ 0)  
   * Move k buyers from the end of this queue to the end of the defector-buyer queue  
* Iterate over the queues of all the cashboxes in ascending order by number, and do the following for each:
   * Calculate how many buyers must be added to the queue—for example, _k_ buyers (_k_ ≥ 0)  
   * Move _k_ buyers from the head of the defector-buyer queue to the end of this queue (remember that a queue with a state of `IS_CLOSING` cannot be extended)  


## Restrictions:
You may not use lambdas or streams while doing this task.

## Example
Assume that a letter indicates a buyer, a '+' sign is an available cashbox, and a '-' sign is an unavailable cashbox. Suppose the initial state of the cashboxes is the following:
```
#0[+] ABCDE
#1[+] 
#2[+] FGHI
#3[+] 
#4[-] 
```
The method `tact()` serves two buyers—A and F—and redistributes the queues. When A and F are removed, the total number of buyers will be 7. The cashbox at number 4 is in the `DISABLED` state, so the minimum number of buyers in the queue is 1, and the maximum is 2.
```
#0[+] BC  (DE ==> defector buyers)
#1[+] 
#2[+] GH  (I ==> defector buyers)
#3[+] 
#4[-] 
```

`defector-buyers` = [E, D, I]

After the redistribution of defector-buyers, the queues might look like this:
```
#0[+] BC
#1[+] ED
#2[+] GH
#3[+] I
#4[-] 
```
#### Note.  
    If a cashbox is in the IS_CLOSING state, its queue cannot be increased; it can only be decreased.

## Output example
The `main` method of the `Shop` class must produce the following output:
```
Initial state
#0[-] 
#1[-] 
#2[-] 
#3[-] 
#4[-] 
==============
Enable cboxes 0, 2, 4
#0[+] 
#1[-] 
#2[+] 
#3[-] 
#4[+] 
==============
Add 5 buyers
#0[+] AD
#1[-] 
#2[+] BE
#3[-] 
#4[+] C
==============
Add 8 buyers (total 13 buyers)
#0[+] ADGJM
#1[-] 
#2[+] BEHK
#3[-] 
#4[+] CFIL
==============
Set cbox #2 is closing
#0[+] ADGJM
#1[-] 
#2[|] BEHK
#3[-] 
#4[+] CFIL
==============
Do 1 tact
#0[+] DGJM
#1[-] 
#2[|] EHK
#3[-] 
#4[+] FIL
==============
Enable cbox #1, add 7 buyers
#0[+] DGJMS
#1[+] NOPQT
#2[|] EHK
#3[-] 
#4[+] FILR
==============
Enable cbox #3, do 1 tact
#0[+] GJM
#1[+] OPQ
#2[|] HK
#3[+] ST
#4[+] IL
==============
Do 1 tact
#0[+] JM
#1[+] PQ
#2[|] K
#3[+] T
#4[+] L
==============
Do 1 tact
#0[+] M
#1[+] Q
#2[-] 
#3[+] 
#4[+] 
```
