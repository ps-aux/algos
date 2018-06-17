import React from 'react'
import { multiply, range, max, reduce } from 'ramda'
import { List } from 'src/domain/data-structures/list'
import { sorts } from 'src/domain/algorithms/sorting/impl/index'
import { play } from 'src/domain/algorithms/player'
import { shuffle } from 'src/domain/algorithms/shuffle'
import ListView from 'src/domain/data-structures/list/components/List'
import View from 'src/components/basic/View'
import ButtonPanel from 'src/components/basic/ButtonPanel'

const maxVal = reduce(max, 9)

const data = shuffle(List(
    range(0, 100).map(multiply(3))
))

const height = maxVal(data)

const sortActions = Object.entries(sorts)
    .map(([k, v]) => ({
        label: k,
        sort: v
    }))

class Sorting extends React.Component {

    state = {
        sorting: false,
        items: data
    }

    onSort = ({sort}) => {
        this.setState({sorting: true})
        play({
            ...sort(data.clone()),
            tempo: 1
        }).subscribe({
            next: items => this.setState({items}),
            complete: () => this.setState({sorting: false})
        })

    }

    render = () => {
        const {sorting, items} = this.state
        return <View>
            <ButtonPanel actions={sortActions}
                         enabled={!sorting}
                         onClick={this.onSort}/>
            <ListView items={items}
                      height={height}
                      width={15 * data.length}
                      itemWidth={10}/>
        </View>
    }

}

export default Sorting

