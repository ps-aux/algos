import React from 'react'
import {multiply, range} from 'ramda'
import {List} from 'src/algo/list'
import {sorts} from 'src/algo/sorting/index'
import {player} from 'src/algo/player'
import {shuffle} from 'src/algo/shuffle'
import ListView from 'src/components/list/List'
import View from 'src/components/basic/View'
import ButtonPanel from 'src/components/basic/ButtonPanel'

const data = shuffle(List(
    range(0, 100).map(multiply(3))
))

const sortActions = Object.entries(sorts)
    .map(([k, v]) => ({
        label: k,
        sort: v
    }))

class Sorting extends React.Component {

    state = {sorting: false}

    onRenderer = render => {
        this.renderList = render
    }

    componentDidMount() {
        this.renderList(data)
    }

    onSort = ({sort}) => {
        const {steps} = sort(data.clone())
        const play = player({
            steps,
            playStep: this.renderList,
            tempo: 5,
            onDone: () => this.setState({sorting: false})
        })

        this.setState({sorting: true})
        play()
    }

    render = () => {
        const {sorting} = this.state
        return <View>
            <ButtonPanel actions={sortActions}
                         enabled={!sorting}
                         onClick={this.onSort}/>
            <ListView rendererRef={this.onRenderer}/>
        </View>
    }

}

export default Sorting

