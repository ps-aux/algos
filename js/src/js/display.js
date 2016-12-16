import assert from 'assert'

export default class Display {

    constructor(container, elCount) {
        this.canvas = container.querySelector('canvas')
        this.ctx = this.canvas.getContext('2d')
        this.elCount = elCount

        console.log('container width is ', container.offsetWidth)
        this.calculateWidths(container.offsetWidth)

        this.color = '#C33632'
        this.selectedColor = '#5F5F5F'
        this.examinedColor = '#C0C0C0'


        window.onresize = e => {
            const width = container.offsetWidth
            this.calculateWidths(width)
        }
    }

    calculateWidths(containerWidth) {
        const width = containerWidth - 10 // minus padding
        const maxSpace = width / this.elCount
        this.scale = width > 500 ? 2 : 1.5

        this.canvas.height = this.scale * 100
        this.canvas.width = width

        this.spacing = maxSpace > 7 ? 2 : 1

        this.colWidth = maxSpace - this.spacing
    }

    /**
     * ============ Public API
     */


    /**
     *
     * @param array
     */

    setArrayModel(model) {
        this.array = model
        this.drawArray()
    }

    /**
     * =========== Private methods
     */

    drawArray() {
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        this.array.forEach((el, i) => this.draw(el, i))
        window.requestAnimationFrame(() => this.drawArray())
    }

    draw(el, index) {
        var color = el.selected ? this.selectedColor : this.color
        if (el.examined)
            color = this.examinedColor

        this.ctx.fillStyle = color


        const x = this.spacing + (this.colWidth + this.spacing) * index
        const y = 0
        this.ctx.fillRect(x, this.canvas.height - el.val * this.scale,
            this.colWidth, this.canvas.height);
    }

}