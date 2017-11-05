import React from 'react'
import PropTypes from 'prop-types'

const Tree = ({root}) =>
    <div className="tree">
        <Node {...root}/>
    </div>
Tree.propTypes = {
    root: PropTypes.object.isRequired
}

const MaybeNode = ({node}) =>
    node ? <Node {...node}/> : <NoNode/>


const Node = ({value, left, right}) =>
    <div className="node">
        <div className="value">{value}</div>
        <div className="children">
            <MaybeNode node={left}/>
            <MaybeNode node={right}/>
        </div>
    </div>

const NoNode = () =>
    <div className="node no-node">
    </div>

export default Tree