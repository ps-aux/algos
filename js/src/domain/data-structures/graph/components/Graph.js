import React from 'react'
import * as d3 from 'd3'
import { prop, range } from 'ramda'
import Collection from 'src/components/basic/Collection'
import styled from 'styled-components'

const layout = (graph, {height, width}) => {
    const simulation = d3.forceSimulation()
        .alphaMin(1)
        .force('link', d3.forceLink()
            .id(d => d.id)
            .distance(prop('value')))
        .force('charge', d3.forceManyBody())
        .force('center', d3.forceCenter(width / 2, height / 2))

    simulation.nodes(graph.nodes)

    simulation.force('link').links(graph.links)

    range(1, 100)
        .forEach(() => {
            simulation.tick()
        })

    return graph
}

const pickColor = ps => ps.selected ? 'red' : 'black'

const Circle = styled.circle`
    fill: ${pickColor}
    cursor: ${ps => ps.onClick ? 'pointer' : 'default'}
`

const Node = ({x, y, r = 5, onClick, ...rest}) =>
    <Circle {...rest}
            cx={x}
            cy={y}
            r={r}
            onClick={onClick}/>

const Line = styled.line`
    stroke: ${pickColor}
    stroke-opacity: 0.2
    cursor: ${ps => ps.onClick ? 'pointer' : 'default'}
`

const Link = ({source: s, target: t, onClick, ...rest}) =>
    <Line {...rest}
          x1={s.x} y1={s.y}
          onClick={onClick}
          x2={t.x} y2={t.y}/>

class Graph extends React.Component {

    render () {
        const {data, onNodeClick, height, width} = this.props
        const {nodes, links} = layout(data, {height, width})
        return <svg width={width} height={height}>
            <Collection comp={Link}
                        items={links}/>
            <Collection comp={Node} items={nodes}
                        props={{
                            onClick: onNodeClick
                        }}/>
        </svg>
    }
}

export default Graph
