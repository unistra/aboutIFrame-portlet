About IFrame Portlet
====================

Is a kind of `IFrame` _JSR-168 Portlet_ with some features added (optionally via _portlet preferences_) :


Features
--------
* Only displays a full `IFrame` in Maximized view
  * otherwise displays an __a-propos__ `HTML` text and a link to the maximized view
* Open url in a new browser window instead of an IFrame
* Re-size `IFrame` element dynamically using `jQuery` to avoid double scroll-bars
* handle ABOUT portlet mode (giving access to the _a-propos_ text in maximized view)
* JavaScript rich text editor to edit HTML about text


Plan & Ideas
------------
* build CAS login urls pointing to CAS-enabled services, or better give a proxy-ticket
* record usage Statistics
* memorize navigation using AJAX to persist frame navigation across portal navigation
* provide an optional HELP portlet mode
  * JavaScript rich text editor to edit help HTML parameter
* optionnaly provide IFrame in all cases (like original IFrame Portlet)
* open in a new window in case of mobile devices
* _…_

