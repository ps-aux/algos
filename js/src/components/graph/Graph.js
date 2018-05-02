import React from 'react'
import s from './Graph.sass'
import * as d3 from 'd3'
import {data} from './data'
import {range} from 'ramda'
import cytoscape from 'cytoscape'


const graph = {
    nodes: [
        {id: 1},
        {id: 2},
        {id: 3},
        {id: 4},
        {id: 5}
    ],
    links: [
        {
            source: 1,
            target: 2
        },
        {
            source: 1,
            target: 2
        },
        {
            source: 4,
            target: 5
        },
        {
            source: 2,
            target: 3
        }
    ]
}

const onRef = el => {
    if (!el)
        return

    var svg = d3.select(el),
        width = +svg.attr("width"),
        height = +svg.attr("height")

    var color = d3.scaleOrdinal(d3.schemeCategory20)

    var simulation = d3.forceSimulation()
        .force("link", d3.forceLink().id(function (d) {
            return d.id
        }))
        .force("charge", d3.forceManyBody())
        .force("center", d3.forceCenter(width / 2, height / 2))


    var link = svg.append("g")
        .attr("class", "links")
        .selectAll("line")
        .data(graph.links)
        .enter().append("line")
        .attr("stroke-width", function (d) {
            return Math.sqrt(d.value)
        })

    var node = svg.append("g")
        .attr("class", "nodes")
        .selectAll("circle")
        .data(graph.nodes)
        .enter().append("circle")
        .attr("r", 5)
        .attr("fill", function (d) {
            return color(d.group)
        })
        .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended))

    node.append("title")
        .text(function (d) {
            return d.id
        })

    simulation
        .nodes(graph.nodes)
        .on("tick", ticked)

    simulation.force("link")
        .links(graph.links)

    function ticked() {
        link
            .attr("x1", function (d) {
                return d.source.x
            })
            .attr("y1", function (d) {
                return d.source.y
            })
            .attr("x2", function (d) {
                return d.target.x
            })
            .attr("y2", function (d) {
                return d.target.y
            })

        node
            .attr("cx", function (d) {
                return d.x
            })
            .attr("cy", function (d) {
                return d.y
            })
    }


    function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart()
        d.fx = d.x
        d.fy = d.y
    }

    function dragged(d) {
        d.fx = d3.event.x
        d.fy = d3.event.y
    }

    function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0)
        d.fx = null
        d.fy = null
    }
}


const onRef2 = el => {
    var svg = d3.select(el),
        width = +svg.attr("width"),
        height = +svg.attr("height")

    var color = d3.scaleOrdinal(d3.schemeCategory20)

    const layout = (graph) => {
        return new Promise((resolve) => {
            var simulation = d3
                .forceSimulation()
                .alphaMin(1)
                .force("link", d3.forceLink().id(function (d) {
                    return d.id
                }))
                .force("charge", d3.forceManyBody())
                .force("center", d3.forceCenter(width / 2, height / 2))
                .on("end", resolve(graph))

            simulation.nodes(graph.nodes)
            simulation.force("link").links(graph.links)

            range(1, 200)
                .forEach(() => {
                    simulation.tick()
                })
        })
    }
    const render = (graph) => {

        console.log('graph is', graph)
        var link = d3.select("#target").append("g")
            .attr("class", "links")
            .selectAll("line")
            .data(graph.links)
            .enter().append("line")
            .attr("stroke-width", function (d) {
                return Math.sqrt(d.value)
            })
            .attr("x1", function (d) {
                return d.source.x
            })
            .attr("y1", function (d) {
                return d.source.y
            })
            .attr("x2", function (d) {
                return d.target.x
            })
            .attr("y2", function (d) {
                return d.target.y
            })

        var node = d3.select("#target").append("g")
            .attr("class", "nodes")
            .selectAll("circle")
            .data(graph.nodes)
            .enter().append("circle")
            .attr("r", 5)
            .attr("cx", function (d) {
                return d.x
            })
            .attr("cy", function (d) {
                return d.y
            })
            .attr("fill", function (d) {
                return color(d.group)
            })
    }


    layout(graph)
        .then((graph) => render(graph))
}


const onRef3 = el => {
    const elements = [ // list of graph elements to start with
        { // node a
            data: {id: 'a'}
        },
        { // node b
            data: {id: 'b'}
        },
        { // edge ab
            data: {id: 'ab', source: 'a', target: 'b'}
        }
    ]

    cytoscape({
        container: el,
        elements,
        style: [ // the stylesheet for the graph
            {
                selector: 'node',
                style: {
                    'background-color': '#666',
                    'label': 'data(id)'
                }
            },

            {
                selector: 'edge',
                style: {
                    'width': 3,
                    'line-color': '#ccc',
                    'target-arrow-color': '#ccc',
                    'target-arrow-shape': 'triangle'
                }
            }
        ],
        layout: {
            name: 'grid',
            rows: 1
        }
    })

}

const Graph = () =>
    <div className={s.container}>
        {/*<svg width="960" height="600">*/}
            {/*<g ref={onRef2} id="target" transform="translate(480, 300)">*/}
            {/*</g>*/}
        {/*</svg>*/}
        <div ref={onRef3}/>
    </div>

export default Graph
