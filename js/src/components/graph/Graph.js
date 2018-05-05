import React from 'react'
import s from './Graph.sass'
import * as d3 from 'd3'
import {data} from './data'
import {range} from 'ramda'
import cytoscape from 'cytoscape'


let graph = {
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
            value: 10,
            target: 2
        },
        {
            source: 2,
            value: 1,
            target: 3
        },
        {
            source: 3,
            value: 10,
            target: 4
        },
        {
            source: 4,
            value: 50,
            target: 5
        },
        {
            source: 5,
            value: 100,
            target: 1
        }
    ]
}

const link = (n1, n2) => ({
    source: n1.id,
    target: n2.id,
    value: 20
})

const find = (nodes, x, y) =>
    nodes.find(n => n.x === x && n.y === y)

const linkByCoords = (nodes, [x1, y1], [x2, y2]) =>
    link(find(nodes, x1, y1), find(nodes, x2, y2))

const neighbours = (w, h, [x, y]) => {
    const res = []

    // Left
    if (x > 0)
        res.push([x - 1, y])
    // Right
    if (x < w - h)
        res.push([x + 1, y])
    // Top
    if (y > 0)
        res.push([x, y - 1])
    // Bottom
    if (y < h - 1)
        res.push([x, y + 1])

    return res
}

const calcGraph = (h, w) => {

    const pairs = range(0, h)
        .flatMap(y => range(0, w)
            .map(x => [x, y]))

    const nodes = pairs
        .map(([x, y]) => ({
            x, y, id: y * w + x
        }))


    // Flower test
    // const [first, ...rest] = nodes
    // const links = rest.map(n => link(first, n))

    const links = pairs
        .flatMap(p =>
            neighbours(w, h, p)
                .map(n => [p, n]))
        .map(([c1, c2]) => linkByCoords(nodes, c1, c2))


    return {nodes, links}

}

graph = calcGraph(8, 8)

const tickCount = 100

const onRef = el => {
    if (!el)
        return

    var svg = d3.select(el),
        width = +svg.attr("width"),
        height = +svg.attr("height")

    var color = d3.scaleOrdinal(d3.schemeCategory20)

    var simulation = d3.forceSimulation()
        .force("link",
            d3.forceLink())
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
    if (!el)
        return

    var svg = d3.select(el),
        width = +svg.attr("width"),
        height = +svg.attr("height")

    var color = d3.scaleOrdinal(d3.schemeCategory20)

    const layout = (graph) => {
        return new Promise((resolve) => {
            var simulation = d3
                .forceSimulation()
                .alphaMin(1)
                .force("link", d3.forceLink()
                    .id(d => d.id)
                    .distance(d => d.value)
                )
                .force("charge", d3.forceManyBody())
                .force("center", d3.forceCenter(width / 2, height / 2))
                .on("end", resolve(graph))


            simulation.nodes(graph.nodes)

            simulation.force("link").links(graph.links)

            range(1, tickCount)
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
        <svg width="960" height="600">
            <g ref={onRef2} id="target" transform="translate(480, 300)">
            </g>
        </svg>
    </div>

export default Graph
