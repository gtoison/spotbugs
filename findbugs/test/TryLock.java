import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class TryLock {
  public static void main(String args[]) throws Exception {
	ReentrantLock lock1 = new ReentrantLock();
	ReadWriteLock rwLock = new ReentrantReadWriteLock();
	Lock lock2 = rwLock.readLock();
	Lock lock3 = rwLock.readLock();

	lock1.tryLock();
	lock2.tryLock();
	lock3.tryLock();
  }
}
