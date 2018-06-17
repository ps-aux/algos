export const player = ({steps, playStep, tempo = 500, onDone}) => () => {
    const play = () => {
        const s = steps.shift()
        if (!s)
            return onDone()

        playStep(s)
        setTimeout(play, tempo)
    }
    play()
}