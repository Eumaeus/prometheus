prometheus
==========

Work toward a 21st Century Edition of Aeschylus’ Prometheus Bound.

## What is Necessary for Generic Treatment of Syntax

- CTS must handle Exemplars properly.
- In CITE Manager, automated generation of syntax-tokenized derivative editions.
- [Stop-gap] How to reconcile existing syntax files with editions?
	- Tokenize edition.
	- Token-by-token comparison to syntax.
	- Match first- and last-tokens of citation leaf-nodes.
	- ???
- Visualization of syntax for CTS texts in CiteServlet (demo works, sort of).
- Better Syntax Editing Pipeline:
	- Enter CTS-URN
		- For tokenized edition… get tokens
		- For non-tokenized edition… tokenize
		- Test for "isLeafNode" somehow
	- Do treebank
	- See XML expression (in another jQuery tab?)
		- copy-and-paste, or
		- export single XML
	- CiteManager:
		- Directory of syntax files
		- Generate "syntax.ttl" to be concatenated with final .ttl 
- CiteKit (which needs a total rewrite with Angular.js) view of syntax-objects.
- Diff?
	- Easy: side-by-side view
	- Harder: overlay view
	- Graph-diff 


