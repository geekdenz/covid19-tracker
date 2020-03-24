<script>
	export let name
	// window.android.showToast('Svelte')
	function clickStart(ev) {
		// window.android.showToast('Start tracking')
		alert('before start')
		window.android.startTracking()
		alert('start tracking')
	}
</script>

<main>
	<h1>Covid-19 Tracker</h1>
	<p>Update 2</p>
	<button on:click={clickStart}>Start Tracking</button>
</main>

<style>
	main {
		text-align: center;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;
	}

	h1 {
		color: #ff3e00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}
</style>