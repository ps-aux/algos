import React from 'react'
import {calcLayout} from 'src/components/tree/treeDrawer'
import './Tree.global.sass'
import {translate} from 'src/d3/utils'
import * as d3 from 'd3'
import Collection from 'src/components/basic/Collection'
import {withProps} from 'recompose'
import {equals, identity, prop} from 'ramda'


const Node = ({value, r = 10, x, y, _ref}) =>
    <g className="node" ref={_ref}
       transform={translate({x, y})}>
        <circle r={10}/>
        <text>{value}</text>
    </g>

const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))

const Link = ({d, _ref}) =>
    <path ref={_ref} className="link" d={d}/>


const withTransition = ({
                            transition,
                            hasChanged = equals,
                            attrs = identity
                        }) => Comp => {

    class D3Transition extends React.Component {
        constructor(props) {
            super(props)
            this.state = {...props}
        }

        ref = el => this.el = el


        componentDidUpdate() {
            const {state, props} = this
            if (hasChanged(state, props)) {
                d3.select(this.el)
                    .transition(transition)
                    .attrs(attrs(props))
                    .on('end', () => this.setState({...this.props}))
            }
        }

        render = () => <Comp {...this.state}
                             _ref={this.ref}/>
    }

    return D3Transition

}

const hasChanged = (a, b) => {
    if (!a && !b)
        return false
    if (!a || !b)
        return true

    return a.x !== b.x || a.y !== b.y
}


const transition = d3.transition()
    .duration(300)
    .ease(d3.easeBounce)


const TNode = withTransition({
    transition,
    hasChanged,
    attrs: props => ({transform: translate(props)})
})(Node)

const TLink = withTransition({
    transition,
    hasChanged: (a, b) => a.d !== b.d
})(Link)


const Tree = ({layout}) =>
    <div>
        <svg width="1200" height="800">
            <g className="tree" transform={translate({x: 400, y: 20})}>
                <g className="links">
                    <Collection items={layout.links.map(l => ({
                        ...l,
                        d: link(l)
                    }))}
                                id={({source, target}) => `${source.id}-${target.id}`}
                                comp={TLink}/>
                </g>
                <g className="nodes">
                    <Collection items={layout.nodes}
                                comp={TNode}/>
                </g>
            </g>
        </svg>
    </div>

export default withProps(
    ({
         tree
     }
    ) => ({layout: calcLayout(tree)}))(Tree)