<!DOCTYPE html>
<html>
<head>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/gojs/1.6.7/go-debug.js"></script>
</head>
<body>
<div id="myDiagramDiv"
     style="width:1300px; height:650px; background-color: #DAE4E4;"></div>
</body>
<script>
	var $ = go.GraphObject.make;
	var diagram = $(go.Diagram, "myDiagramDiv",{
	      initialContentAlignment: go.Spot.Center, // center Diagram contents
	      "undoManager.isEnabled": true // enable Ctrl-Z to undo and Ctrl-Y to redo
	    });
	
	// the node template describes how each Node should be constructed
	diagram.nodeTemplate =
	  $(go.Node, "Auto",  // the Shape automatically fits around the TextBlock
	    $(go.Shape, "RoundedRectangle",
	      // bind Shape.fill to Node.data.color
	      new go.Binding("fill", "color")),
	    $(go.TextBlock,
	      { margin: 3 },  // some room around the text
	      // bind TextBlock.text to Node.data.key
	      new go.Binding("text", "key"))
	  );
	
	// the Model holds only the essential information describing the diagram
	diagram.model = new go.GraphLinksModel(
		[ // a JavaScript Array of JavaScript objects, one per node
		  { key: "Alpha", color: "lightblue" },
		  { key: "Beta", color: "orange" },
		  { key: "Gamma", color: "lightgreen" },
		  { key: "Delta", color: "pink" }
		],
		[ // a JavaScript Array of JavaScript objects, one per link
		  { from: "Alpha", to: "Beta" },
		  { from: "Alpha", to: "Gamma" },
		  { from: "Beta", to: "Beta" },
		  { from: "Gamma", to: "Delta" },
		  { from: "Delta", to: "Alpha" }
		]);
	
	var violetbrush = $(go.Brush, go.Brush.Linear, { "0.0": "Violet", "1.0": "Lavender" });
	diagram.add(
			$(go.Node, "Auto",
			  $(go.Shape, "Ellipse",
			    { fill: violetbrush }),
			  $(go.TextBlock, "Goodbye!",
			    { margin: 5 })
			));
</script>
</html>
