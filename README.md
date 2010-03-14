What is JUDAW
=============

The Java UniData API Wrapper is a small collection of Java classes designed to
assist with connecting to and querying off a UniData data source. Example source
can be found at the end of this document.

Dependencies
============

Note that the library is dependent on the Java UniObjects SDK located in
`asjava.zip` from the IBM Developer's Kit (UniDK). This file is included in
the JUDAW repository in the `deps` directory. The version number for the
included version of this file is in the `VERSION` file in the same directory.

Special Thanks
==============

A special thanks to Rich Harrington and others from
[University Web Developers][uwd] for introducing me to the UniObjects API and
for great initial code posts, which inspired this project.

  [uwd]: http://cuwebd.ning.com/ "University Web Developers"

Documentation
=============

JUDAW is documented via JavaDoc style comments in the source code.
HTML documentation built from specific versions of JUDAW can be found
on the "Downloads" tab of this project's [GitHub home][githome].

  [githome]: http://github.com/BinaryMuse/judaw "JUDAW on GitHub"

License
=======

Copyright (c) 2010, Fresno Pacific University

Licensed under the New BSD license; see the LICENSE file for details.

Example Code
============

Creating and Connecting to the UniDataConnection Object
-------------------------------------------------------

    UniDataConnection ud = new UniDataConnection("username", "password",
        "datatel.domain.local", "D:\\account\\path");
    ud.connect();

Accessing the Underlying UniJava Object
---------------------------------------

In version 1.2:

    System.out.println("Using UniData SDK version " + ud.UniJava.getVersionNumber());
    System.out.println("Connection number " + ud.UniJava.getNumSessions() +
        " of " + ud.UniJava.getMaxSessions());

In master and version 1.3+, accessing the UniJava object directly is
deprecated, and you should use the UniJava() getter instead:

    System.out.println("Using UniData SDK version " + ud.UniJava().getVersionNumber());
    System.out.println("Connection number " + ud.UniJava().getNumSessions() +
        " of " + ud.UniJava().getMaxSessions());

Opening a File and Reading a Record
-----------------------------------

    UniSession session = ud.getSession();
    UniFile person = session.open("PERSON");
    person.setRecordID("0123456");
    System.out.println("First Name: " + person.readNamedField("FIRST.NAME"));
    System.out.println("Last Name: " + person.readNamedField("LAST.NAME"));

Selecting Data with SELECT and LIST Wrappers
--------------------------------------------

    // Get a working list
    ud.query("SELECT PERSON WITH @ID EQ '0123456''0654321'");
    // Create a map of Field objects to specify which
    // fields we wish to retrieve data from:
    Map<String,String> fields = new HashMap<String, String>();
    fields.put("FIRST.NAME", "fname");
    fields.put("LAST.NAME", "lname");
    // Note that there are several ways to specify
    // which fields you wish to retrieve; see
    // UniDataConnection#getFields
    
    List<FieldSet> sets = ud.getFields("PERSON", fields);
    if(sets == null)
        System.out.println("No data returned.");
    
    Iterator<FieldSet> iter = sets.iterator();
    while(iter.hasNext())
    {
        // A FieldSet contains information regarding the field name,
        // the friendly field name we used (if any), and the
        // data contained within the field.
        // Each row is turned into a FieldSet, and each FieldSet contains a number
        // of Fields (based on the second parameter to getFields).
        FieldSet set = iter.next();
        System.out.println("First Name: " + set.getFieldByFriendlyName("fname").getData());
        System.out.println("Last Name: " + set.getFieldByName("LAST.NAME").getData());
    }
