## What is it about? ##

This project contains a monthly-view calendar, which is based on the [fullcalendar](http://arshaw.com/fullcalendar/), with drag&drop abilities on the events.
It also supports exporting events of a month to [iCal](http://en.wikipedia.org/wiki/ICal). All actions are implemented via PL. I hope at the final step I'll be merging this goodie to the richfaces-ext project. Here is the look of the component:

![http://jsfcal.googlecode.com/files/jsfcal_shot.jpg](http://jsfcal.googlecode.com/files/jsfcal_shot.jpg)

---


## How to use it with Maven? ##
just add repo definition to your pom.xml
```
<repository>
   <id>jsfcal-repo</id>
   <name>JSFCal Repository</name>
   <url>http://jsfcal.googlecode.com/svn/trunk/mavenrepo/</url>
</repository>
```
and then add the dependency. Then you're good to go.
```
<dependency>
   <groupId>tr.richfacesext</groupId>
   <artifactId>jsfcal-core</artifactId>
   <version>0.1</version>
</dependency>
```
You can also download artifacts [here](http://code.google.com/p/jsfcal/downloads/list).

---

## What do I need on the coding side? ##
You have to supply a `Collection<Event>` as a value to the component. The `tr.richfacesext.components.jsfcal.Event` interface contract is given below. You can find a concrete impl. of `Event` [here](http://jsfcal.googlecode.com/svn/trunk/jsfcal-examples/src/main/java/tr/richfacesext/jsfcal/CalEvent.java).
```
public interface Event extends Serializable {

	Long getEventId();
	
	String getTitle();
	
	String getDescription();
	
	Date getStartDate();
	
	Date getEndDate();
	
	boolean isReadOnly();
}
```
With facelets, add namespace declaration like,
```
xmlns:jsfcal="http://code.google.com/p/jsfcal"
```
and use the month-view component like,
```
<jsfcal:month id="monthView" value="#{monthViewController.events}" />
```

---

## What attributes does the component have? ##
  * **initYear** : The year that will be displayed when the calendar first loads. By default it's the current year.
  * **initMonth** : The month that will be displayed when the calendar first loads. By default it's the current month.
  * **readOnly** : When set to true, events couldn't be dragged. By default it's false.
  * **width** :  Width of the calendar. By default it's 650px.
  * **height** : Height of the calendar. By default it's 550px.
  * **abbrevDayHeadings** : Whether to display 'Sun' versus 'Sunday' for days of the week. By default it's true.
  * **title** : Determines whether a title such as 'January 2009' will be displayed at the top of the calendar. By default it's true.
  * **language** : See Localization section for detailed info.

---

## Localization ##
Component has a property named **language** to set the locale in a declarative way (values are given in supported locales part). If that property is not set, it uses `context.getViewRoot().getLocale().getLanguage()` to identify the locale. If you've provided locale settings in your faces-config properly, the `FacesServlet` will take care of setting it while creating the view. And JSFCal will use it. Start day of the week is being set according to the locale also.


Supported locales are: tr, de, en, es, fr, it, ro, ru.

---

## Comin' up next ##
  * Weekly view
  * Event editing, deleting
  * Outlook export support(?)