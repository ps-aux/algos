import React from 'react'
import {multiply, range} from 'ramda'
import {List} from 'src/algo/list'
import {sorts} from 'src/algo/sorting/index'
import {player} from 'src/algo/player'
import {shuffle} from 'src/algo/shuffle'
import Button from 'src/components/basic/Button'
import s from './ButtonPanel.sass'
import ListView from 'src/components/list/List'

const data = shuffle(List(
    range(0, 100).map(multiply(3))
))

const h = 400
const w = 1400

const ButtonPanel = ({sorts, onClick, enabled = true}) =>
    <div className={s.buttonPanel}>
        {Object.entries(sorts)
            .map(([k, v]) =>
                <Button key={k} label={k}
                        enabled={enabled}
                        onClick={() => onClick(v)}/>)}
    </div>

class Sorting extends React.Component {

    state = {sorting: false}

    onRenderer = render => {
        this.renderList = render
    }

    componentDidMount() {
        this.renderList(data)
    }

    onSort = sort => {
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
        return <div>
            <ButtonPanel sorts={sorts}
                         enabled={!sorting}
                         onClick={this.onSort}/>
            <ListView rendererRef={this.onRenderer}/>
        </div>
    }

}

export default Sorting

