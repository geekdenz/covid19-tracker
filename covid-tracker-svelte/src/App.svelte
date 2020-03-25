<script>
	let tracking = false
	function clickStart(ev) {
		window.android.startTracking()
		window.android.showToast('Starting tracking...')
		tracking = true
	}
	function clickStop(ev) {
		window.android.stopTracking()
		window.android.showToast('Stopping tracking...')
		tracking = false
	}
</script>

<main>
	<h1>Covid-19 Tracker</h1>
	<p>Update 4</p>
	{#if tracking }
	<p>TRACKING</p>
	{:else}
	<p>NOT TRACKING</p>
	{/if}
	<button on:click={clickStart}>START Tracking</button>
	<button on:click={clickStop}>STOP Tracking</button>
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