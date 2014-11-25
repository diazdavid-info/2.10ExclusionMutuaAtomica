package david;

import java.util.concurrent.atomic.AtomicLong;

public class ExclusionMutuaAtomica implements Runnable {
	
	public static final int NUMERO_SUMADO = 10000;
	public static final long NUM_VECES = 10000;
	
	private volatile AtomicLong _suma = new AtomicLong(0);
	
	@Override
	public void run() {
		for (int i = 1; i <= NUM_VECES; i++) {
			_suma.addAndGet(NUMERO_SUMADO);
		}
	}
	
	public long getSuma() {
		return _suma.get();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ExclusionMutuaAtomica race = new ExclusionMutuaAtomica();
		Thread t1, t2;
		
		t1 = new Thread(race);
		t2 = new Thread(race);
		
		t1.start();
		t2.start();
		
		long resultadoEsperado = NUMERO_SUMADO * NUM_VECES * 2;
		
		t1.join();
		t2.join();
		
		System.out.println("El resultado final es "+race.getSuma());
		System.out.println("Esperamos "+resultadoEsperado);
		
		if(race.getSuma() != resultadoEsperado){
			System.out.println("NO COINCIDE");
		}
	}

	

}
