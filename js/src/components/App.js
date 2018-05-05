import React from 'react'

import {heap} from 'src/algo/data-structures/heap'
import {range} from 'ramda'
import Sorting from 'src/components/sorting/Sorting'
import {Route, withRouter} from 'react-router'
import View from 'src/components/basic/View'
import {Link} from 'react-router-dom'
import Bst from 'src/components/tree-algos/Bst'
import Graph from 'src/components/graph/Graph'
import {Menu} from 'semantic-ui-react'
import Page from 'src/components/Page'


const h = heap()

range(1, 10).forEach(v => h.insert(v))

const activeItem = 123

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
        path: '/graph',
        title: 'Graph',
        component: Graph
    }
]

const App = () =>
    <View className="app">
        <Menu fixed>
            {pages.map(({title, path}) =>
                <NavMenuItem name={title} path={path}/>)}
        </Menu>
        {pages.map(p => <Page {...p}/>)}
    </View>

export default App


const NavMenuItem = withRouter(({location, path, name}) =>
    <Link to={path}>
        <Menu.Item name='editorials'
                   active={location.pathname.startsWith(path)}>
            {name}
        </Menu.Item>
    </Link>)
