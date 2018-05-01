import React from 'react'
import 'd3-selection-multi'
import '../d3.sass'
import treeDrawer from 'src/components/tree/treeDrawer'


const Tree = ({renderRef}) =>
    <div className="tree">
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