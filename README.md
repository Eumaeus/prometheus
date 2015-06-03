Prometheus Bound
==========

Work toward a 21st Century Edition of Aeschylus’ Prometheus Bound.

## Prometheus / eComparatio CTS Service

As we further develop editions of the *Prometheus Bound* we will expose them through a [CTS Service](http://www.homermultitext.org/hmt-doc/cite/texts/cts.html). 

### The CTS Service

The base address for the CTS Service is:

> [http://folio.furman.edu/ecomp-cts/api?](http://folio.furman.edu/ecomp-cts/api?)

This implementation does not apply stylesheets for formatting XML output; this is by design.

Some sample requests:

- [Get Capabilities](http://folio.furman.edu/ecomp-cts/api?request=GetCapabilities)
- [Get Valid Citations for the Furman edition, based on Smyth](http://folio.furman.edu/ecomp-cts/api?request=GetValidReff&level=1&urn=urn:cts:greekLit:tlg0085.tlg003.fu:)
- [Get the text of lines 1-10 of the Furman edition](http://folio.furman.edu/ecomp-cts/api?request=GetPassage&urn=urn:cts:greekLit:tlg0085.tlg003.fu:1-10]
- [Get Valid Citations for the Schütz edition of 1782, not complete at this point](http://folio.furman.edu/ecomp-cts/api?request=GetValidReff&level=1&urn=urn:cts:greekLit:tlg0085.tlg003.schutz1782:)
- [Get the text of lines 1-10 of the Schütz edition](http://folio.furman.edu/ecomp-cts/api?request=GetPassage&urn=urn:cts:greekLit:tlg0085.tlg003.schutz1782:1-10)

### The SparQL Endpoint

The CTS Service draws its data from a SPARQL endpoint. [This is publically exposed here.](http://folio.furman.edu/snorql/)

### The TTL Data

The `.ttl` file containing the RDF that is behind the SparQL endpoint is at `ttl/cts.ttl` in this repository.

### The XML Data

The XML files that are the basis for the RDF are in `Editions/*`. The XML of the TextInventory file is at `Inventory/inventory.xml`. As part of processing the XML texts into RDF, we capture their semantics in tabulated plain-text files. These are at `tabulated/*.txt` in this repository.



