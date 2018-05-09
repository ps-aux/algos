import React from 'react'
import treeDrawer, {calcLayout} from 'src/components/tree/treeDrawer'
import './Tree.global.sass'
import {translate} from 'src/d3/utils'
import * as d3 from 'd3'
import Collection from 'src/components/basic/Collection'
import {withProps} from 'recompose'
import {prop} from 'ramda'
import Animate from 'react-move/Animate'
import {easeExpOut} from 'd3'


const Node = ({value, r = 10, x, y, _ref}) =>
    <g className="node" ref={_ref}
       transform={translate({x, y})}>
        <circle r={10}/>
        <text>{value}</text>
    </g>

const link = d3.linkVertical()
    .x(prop('x'))
    .y(prop('y'))

const Link = props =>
    <path className="link" d={link(props)}/>


const TNode = props =>
    <Animate
        start={() => props}
        update={() => ({
            x: [props.x],
            timing: {duration: 750, ease: easeExpOut}
        })}>
        {s => <Node {...s} />}
    </Animate>

const TLink = props =>
    <Animate
        start={() => props}
        update={() => ({
            source: [{x: props.source.x, y: props.source.y}],
            target: [{x: props.target.x, y: props.target.y}],
            timing: {duration: 750, ease: easeExpOut}
        })}>
        {s => <Link {...s} />}
    </Animate>



const Tree = ({layout}) =>
    <div>
        <svg width="1200" height="800">
            <g className="tree" transform={translate({x: 400, y: 20})}>
                <g className="links">
                    <Collection items={layout.links}
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
    ) =>
        ({layout: calcLayout(tree)})
)
(Tree)