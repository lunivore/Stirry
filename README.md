# Stirry
A very lightweight JavaFX automation tool.

Current features:

- Find the root node of your app, or modal dialogs
- Find any other node using predicates
- Wait for nodes to be in a particular state, returning any arbitrary result you like
- Wait for text property changes (faster)
- Activate controls and wait until they've notified their listeners and the platform is idle again ("XAndStir()")
- Pick dates from datepickers with a string
- Fire buttons
- Check or clear checkboxes
- ...all using extensions of the relevant controls.

Examples of how to use it are [here](src/test/kotlin/com/lunivore/stirry/).
