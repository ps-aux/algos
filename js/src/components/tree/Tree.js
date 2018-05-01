import React from 'react'
import treeDrawer from 'src/components/tree/treeDrawer'
import './Tree.global.sass'

const Tree = ({renderRef}) =>
    <div>
        <svg ref={el => {
            renderRef(treeDrawer(el))
        }} width="1200" height="800">
            <g className="tree">
                <g className="links"/>
                <g className="nodes"/>
            </g>
        </svg>
    </div>

export default Tree