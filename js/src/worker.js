console.log('this is web worker')

onmessage = e => {
    console.log('Message received from main script')
    console.log('Posting message back to main script')
    postMessage(123)
}