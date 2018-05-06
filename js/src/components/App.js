import React from 'react'

import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'
import Sorting from 'src/components/sorting/Sorting'
import {Route, withRouter} from 'react-router'
import View from 'src/components/basic/View'
import Bst from 'src/components/tree-algos/Bst'
import Graph from 'src/components/graph/Graph'
import {Menu} from 'semantic-ui-react'
import Page from 'src/components/Page'
import {NavMenuItem} from 'src/components/NavMenu'


const h = heap()

range(1, 10).forEach(v => h.insert(v))

const pages = [
    {
        path: '/sorting',
        title: 'Sorting',
        component: Sorting
    },
    {
        path: '/bst',
        title: 'Binary search tree',
        component: Bst
    },
    {
        path: '/graph/:type?',
        link: '/graph/',
        title: 'Graph',
        component: Graph
    }
]

const App = () =>
    <View className="app">
        <Menu fixed="top">
            {pages.map(({title, path, link}) =>
                <NavMenuItem name={title}
                             key={link || path}
                             path={link || path}/>)}
        </Menu>
        {pages.map(p => <Page
            key={p.path}
            {...p}/>)}
    </View>

export default App


