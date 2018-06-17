import React from 'react'
import { NavMenuItem } from 'src/components/NavMenu'
import { Menu } from 'semantic-ui-react'
import Button from 'src/components/basic/Button'
import View from 'src/components/basic/View'
import bfs from 'src/domain/algorithms/graph/bfs'
import { radialGraph } from 'src/domain/data-structures/graph/data/radial'
import { randomGraph } from 'src/domain/data-structures/graph/data/random'
import { gridGraph } from 'src/domain/data-structures/graph/data/grid'
import Graph from 'src/domain/data-structures/graph/components/Graph'
import dfs from 'src/domain/algorithms/graph/dfs'

const types = {
    grid: () => gridGraph(8, 8),
    random: () => randomGraph(10, 10),
    radial: () => radialGraph(30)
}

const createGraph = type => {
    const {nodes, links} = types[type]()

    // Add indexes
    return {
        nodes: nodes.map((n, index) =>
            ({...n, index})),

        links: links.map((n, index) =>
            ({...n, index}))
    }
}

const pathAlg = dfs

class GraphPage extends React.Component {

    state = {
        selecting: false,
        selection: null,
        key: 0
    }

    static getDerivedStateFromProps (p, s) {
        const {type = 'grid'} = p
        if (type === s.type)
            return null

        const graph = createGraph(type)
        return {
            type,
            graph,
            key: s.key + 1
        }
    }

    startSelecting = () => {
        const {graph} = this.state
        const {nodes, links} = graph

        // Clear previous selection
        nodes.forEach(n => n.selected = false)
        links.forEach(n => n.selected = false)
        this.setState({
            graph,
            selecting: true,
            selection: {
                from: null,
                to: null
            }
        })
    }

    onNodeSelect = n => {
        this.markSelected(n)
        const {selection: sel} = this.state

        if (!sel.from)
            sel.from = n
        else if (!sel.to)
            sel.to = n

        if (sel.to && sel.from) {
            const from = sel.from.id
            const to = sel.to.id
            const {links, nodes} = this.state.graph
            const p = pathAlg({
                nodes,
                links: links.map(l => ({
                    source: l.source.id,
                    target: l.target.id
                }))
            }, from, to)

            p.forEach(n => n.selected = true)

            let n = p.pop()
            while (p.length > 1) {
                let prev = p.pop()

                const l = links.find(l =>
                    l.source === prev && l.target === n ||
                    l.target === prev && l.source === n
                )
                if (!l) {
                    console.log(links)
                    console.log(links.filter(l => l.source === prev))
                }
                l.selected = true

                n = prev
            }

            this.setState({
                graph: this.state.graph,
                selection: null,
                selecting: false
            })
        }

        this.setState({
            graph: this.state.graph,
            selection: sel
        })

    }

    markSelected = (...ns) => {
        const {nodes} = this.state.graph
        ns.forEach(n =>
            nodes[n.index].selected = !n.selected)

    }

    render () {
        const {graph, selecting, selection, key} = this.state
        return <View>
            <Menu>
                <NavMenuItem name="Grid" path="grid"/>
                <NavMenuItem name="Radial" path="radial"/>
                <NavMenuItem name="Random" path="random"/>
            </Menu>
            <Button label="Path" onClick={this.startSelecting}/>
            <Graph data={graph}
                   onNodeClick={selecting ? this.onNodeSelect : null}
                   k={key}
                   height={600}
                   width={960}/>
            {selecting && <View>
                Pick a {selection.from ? 'target' : 'source'} node
            </View>}
        </View>

    }
}

export default GraphPage
