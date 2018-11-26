import { rxPlayer } from 'src/domain/algorithms/player'

it.skip('wowow', done => {
    return
    const arr = [7, 5, 4]

    let count = 0
    rxPlayer({steps: arr, tempo: 100})
        .subscribe({
            next: x => {
                console.log('next', x)
                count++
            },
            complete: () => {
                console.log('we done')
                expect(count).toBe(3)
                done()
            }
        })

})