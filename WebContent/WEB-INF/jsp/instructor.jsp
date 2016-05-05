<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="css/navbar.css">
    <title>Welcome!</title>
</head>
<style>

.bar {
  fill: steelblue;
}

.bar:hover {
  fill: orange;
}

.axis text {
  font: 10px sans-serif;
}

.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

.x.axis path {
  display: none;
}


.d3-tip {
  line-height: 1;
  font-weight: bold;
  padding: 8px;
  background: rgba(0, 0, 0, 0.8);
  color: #fff;
  border-radius: 2px;
}

/* Creates a small triangle extender for the tooltip */
.d3-tip:after {
  box-sizing: border-box;
  display: inline;
  font-size: 10px;
  width: 100%;
  line-height: 1;
  color: rgba(0, 0, 0, 0.8);
  content: "\25BC";
  position: absolute;
  text-align: center;
}

/* Style northward tooltips differently */
.d3-tip.n:after {
  margin: -1px 0 0 0;
  top: 100%;
  left: 0;
}


</style>


<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                    <b>Study Guide |</b>
                </a>
            </div>
            <div>
                <ul class="nav navbar-nav">
                    <li><a href="#">Visualize</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                	<li><a href="#">
                	 ${sessionUser.getFname()}</a>
                	</li>
                    <li>
                        <a href="<c:url value= 'j_spring_security_logout' />">
                            <span class="glyphicon glyphicon-log-out"></span> Logout</a>
                    </li>
                </ul>
            </div>

        </div>
    </nav>
    <div class="container">
        <h2>Welcome ${sessionUser.getFname()}</h2>
        <br>
        <center>
            <div class="well">
                <table class = "table table-bordered table-condensed">
                    <th>
                        <td><h3>Your role:</h3></td>
                        <td><h3>Instructor</h3></td>
                    </th>
                </table><br><br>
                <a class="btn btn-danger" href="<c:url value= 'j_spring_security_logout' />"> Logout</a>
            </div>

        </center>

    </div>
    <div>
<table>
<tr>
<td>
<svg class="chart"></svg>
</tr>
</table>
</div>
<script src="scripts/d3.v3.min.js" charset="utf-8"></script>
<script src="scripts/d3.tip.v0.6.3.js"></script>
<script>

var margin = {top: 20, right: 0, bottom: 45, left: 30},
    width = 260 - margin.left - margin.right,
    height = 600 - margin.top - margin.bottom;

var x = d3.scale.ordinal()
    .rangeRoundBands([0, width], .3, .5);

var y = d3.scale.linear()
    .range([height, 0]);

var xAxis = d3.svg.axis()
    .scale(x)
    .orient("bottom");

var yAxis = d3.svg.axis()
    .scale(y)
    .orient("left");

var chart = d3.select(".chart")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

var tip = d3.tip()
      .attr('class', 'd3-tip')
      .offset([-10, 0])
      .html(function(d) {
          return "<strong>Mid term 1:</strong> <span style='color:red'>" + d[2]["Mid term 1"] + "</span> <br/> <strong>Mid term 2:</strong> <span style='color:red'>" + d[2]["Mid term 2"] + "</span> <br/> <strong>Mid term 3:</strong> <span style='color:red'>" + d[2]["Mid term 3"] + "</span> <br/> <strong>Final exam:</strong> <span style='color:red'>" + d[2]["Final exam"] + "</span>";
    });


function loadData(path) {
    d3.json(path, function(data) {
        dataset = data.students.map(function(d) { return [ d.name, +d.originalityscore, d.values ]; });

        x.domain(dataset.map(function(d) { return d[0]; }));
        y.domain([0, d3.max(dataset, function(d) { return d[1]; })]);
        var bar = chart.selectAll(".bar")
                         .data(dataset, function(d) {return d[0];});
        bar.enter().append("rect")
            .attr("class", "bar")
            .attr("x", function(d) { return x(d[0]); })
            .attr("y", function(d) { return y(d[1]); })
            .attr("height", function(d) { return height - y(d[1]); })
            .attr("width", x.rangeBand())
            .on("mouseover", tip.show)
            .on("mouseout", tip.hide);

            bar.exit().remove();

            bar.transition().duration(1000).attr("y", function(d) {return y(d[1]);})
               .attr("height", function(d) {return height - y(d[1])})
               .attr("x", function(d) { return x(d[0]); });

            chart.select(".x.axis").remove();
            chart.select(".y.axis").remove();

            chart.append("g")
                .attr("class", "x axis")
                .attr("transform", "rotate(90)")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
                .selectAll("text")
                .attr("x", 3)
                .attr("dy", "-0.55em")
                .attr("dx", ".35em")
                .attr("transform", "rotate(90)")
                .style("text-anchor", "start");

            chart.append("g")
                .attr("class", "y axis")
                .call(yAxis)
                .append("text")
                .text("Originality Index")
                .attr("transform", "rotate(-90)")
                .style("text-anchor", "end")
                .attr("y", 16);


    });
}
loadData("/StudyGenie/visualize");
chart.call(tip);
</script>
</body>
</html>