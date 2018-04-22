import React from 'react'
// We cannot use the name _Array or we get an error. Stange :|
import Arraj from 'src/components/list/_Array'
import {bubbleSort, partition, insertionSort, selectionSort, quickSort} from 'src/algo/sorting/index'
import {randomArray} from 'src/algo/array'

const array = randomArray(20)

const sort = a => quickSort(a, 0, a.length)

class _ArraySort extends React.Component {

    state = {array}

    sort = () => {
        const array = this.state.array.slice()
        sort(array)
        this.setState({array})
    }

    render() {

        const {array} = this.state

        return <div className="array-sort">
            <Arraj array={array}/>
            <button onClick={this.sort}>
                Sort
            </button>
        </div>
    }
}

export default _ArraySort