package com.epam.bsp.linked.list;
import java.util.List;
public class CustomLinkedList < E >
{
  protected static class CustomListNode < E >
  {
    protected E element;
    protected CustomListNode < E > next;
    public CustomListNode (E element, CustomListNode < E > next)
    {
      this.element = element;
      this.next = next;
  }} protected CustomListNode < E > head;
  public CustomLinkedList (List < E > elements)
  {
    if (elements == null || elements.isEmpty ())
      {
	head = null;
      }
    else
      {
	head = new CustomListNode <> (elements.get (0), null);
	CustomListNode < E > current = head;
	for (int i = 1; i < elements.size (); i++)
	  {
	    current.next = new CustomListNode <> (elements.get (i), null);
	    current = current.next;
  }}} public CustomLinkedList < E > removeNodes (E element)
  {
    CustomListNode < E > fakeHead = new CustomListNode <> (null, head);
    CustomListNode < E > prev = fakeHead;
    CustomListNode < E > current = head;
    while (current != null)
      {
	if (current.element.equals (element))
	  {
	    prev.next = current.next;
	  }
	else
	  {
	    prev = current;
	  }
	current = current.next;
      }
    head = fakeHead.next;
    return this;
  }
  public CustomLinkedList < E > reverse ()
  {
    CustomListNode < E > prev = null;
    CustomListNode < E > current = head;
    CustomListNode < E > next = null;
    while (current != null)
      {
	next = current.next;
	current.next = prev;
	prev = current;
	current = next;
      }
    head = prev;
    return this;
  }
  public CustomLinkedList < E > getRightMiddle ()
  {
    CustomListNode < E > slow = head;
    CustomListNode < E > fast = head;
    while (fast != null && fast.next != null)
      {
	fast = fast.next.next;
	slow = slow.next;
      }
    CustomLinkedList < E > result = new CustomLinkedList <> (null);
    result.head = slow;
    return result;
  }
  public boolean check (List < E > elements)
  {
    CustomListNode < E > curr = head;
  for (E element:elements)
      {
	if (curr == null)
	  {
	    return false;
	  }
	if (!element.equals (curr.element))
	  {
	    return false;
	  }
	else
	  {
	    curr = curr.next;
	  }
      }
    return curr == null;
  }
}
