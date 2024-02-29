MessageGenerator: Java desktop app example
--
<h4>General description</h4>
The application shows the possibilities of building desktop Swing applications with using Spring framework:

<ul>
    <li>
        Saving XML documents with attachments to database tables, pagination;
    </li>
    <li>
        Viewing BLOB content of generated documents in different encodings;
    </li>
    <li>
       Support for multi-file attachments of arbitrary size, packed in zip and gzip archives;
    </li>
    <li>
       Editable metadata of XML documents (Content-Type, names);
    </li>
    <li>
        Built-in Hypersonic 2 database for storing database connection configurations, dynamic configuration switching;
    </li>
    <li>
        Viewing the V$SQL SQL log with full-text search by query text;
    </li>
    <li>
        Built-in log viewer with auto-update.
    </li>
</ul>

<h4>Architecture</h4>
The example is built with using Spring and multi-tier architecture (n-tier architecture) and consists of 4 levels:

- gui
- services
- dao
- core

The direction of dependencies is from the gui level to the core level, with each level depending only on the next level. 
All the business values are placed in separate threads from the Event Dispatch Thread.

<h4>Building</h4>

- Java 8: `mvn clean package`
- Java 7: `mvn -Dhttps.protocols=TLSv1.2 clean package`

If the build is successful, the executable fat jar will appear in the gui/target directory.

<h4>Distribution directories</h4>
The __distrib contains the following directories. 
<ul>
    <li><b>db</b> - this directory contains a file of the built-in H2 database MessageGenerator.mv.db, where stored a dump of configurations, 
                    the path to local transport directories and a list of available UI themes. The directory also contains scripts that are 
                     executed every time the application starts (scripts/update) and once when creating and initializing the embedded database (scripts/init).
    </li>
    <li><b>temp</b> - the service directory where archives are unpacked when generating attachments. The content can and should be deleted periodically.</li>
    <li><b>logs</b> - the directory contains the application's operation log. Logs are rotated by size and date. Similarly, it is advisable to delete the content.</li>
</ul>
