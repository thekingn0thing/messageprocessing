## Message Processing System 

The system record and process message of 3 types from incoming file.  

## Build

Pre-requests: Maven 3

```bash
git clone [url]
cd messageprocessing
mvn install
``` 

## Run 

Run example file:

```bash
cd target
java -jar messageprocessing-1.0-SNAPSHOT.jar
``` 

Run with custom file:

```bash
java -jar messageprocessing-1.0-SNAPSHOT.jar [path to file]
```


## Message Format 

The system uses simplified [FIX-like](http://www.fixtradingcommunity.org) message format. Each message contains tag number and value. Main different that only few tags are supported and that message should start always from Message Type(35) tag. 

### Supported Message Types:

<table>
<tr> 
<th>Name</th>
<th>Value</th>
<th>Notes</th>
</tr>
<tr>
<td>Single Order</td>
<td>1</td>
<td>Tag Quantity(53) is ignored for this message type</td>
</tr>
<tr>
<td>Quantity order</td>
<td>2</td>
<td></td>
</tr>
<tr>
<td>Adjustment</td>
<td>3</td>
<td>Adjusment operation will be applied to all existed order wit h same symbol. If orders with such symbol does nto existh then adjustment is ignored and not stored.</td>
</tr>
</table>

### Sported tags

* Message Type(35)
* Quantity(53)
* Price(44)
* Symbol(55)
* Adjustment Type(334)