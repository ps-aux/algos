import React from 'react'
import * as d3 from 'd3'

const draw = (el, list,
              {
                  width = 10,
                  margin = width / 5
              } = {}) => {
    const svg = d3.select(el)

    const y = el.getAttribute('height')


    const bars = svg.selectAll('rect')
        .data(list)

    bars.enter()
        .append('rect')
        .merge(bars)
        .attrs((d, i) => ({
            x: i * (width + margin),
            y: y - d,
            height: d,
            width
        }))

    bars.exit().remove()

}

const create = (el) => {
    return data => draw(el, data)
}


export default create
