
import numpy as np
import time
import threading
import asyncio
from multiprocessing import Process

def mean(num): # find average using sequential
  total = 0
  for t in num:
    total += t
  mean = total / len(num)
  return mean

def sequential_wrapper(list1, list2, list3): # calculate the time
  s = time.perf_counter()
  mean(list1)
  mean(list2)
  mean(list3)
  elapsed = time.perf_counter() - s
  return elapsed

async def async_mean(num): # find average using async
  total = 0
  for t in num:
    total = total + t
  mean = total / len(num)
  return mean

async def async_wrapper(list1, list2, list3): # calculate the time
  s = time.perf_counter()
  tasks = [async_mean(list1), async_mean(list2), async_mean(list3)]
  await asyncio.gather(*tasks)

  elapsed = time.perf_counter() - s
  return elapsed

def threading_wrapper(list1, list2, list3): # calculate the time
  s = time.perf_counter()
  lists = [list1, list2, list3]
  threads = []
  for i in range(len(lists)):
    t = threading.Thread(target = mean, args = (lists[i],))
    threads.append(t)
    t.start()
  for t in threads:
    t.join()
  elapsed = time.perf_counter() - s
  return elapsed

def multiprocessing_wrapper(list1, list2, list3): # calculate the time
  s = time.perf_counter()
  lists = [list1, list2, list3]
  processes = [Process(target = mean, args = (lists[x],)) for x in range(len(lists))]
  for p in processes:
    p.start()
  for p in processes:
    p.join()
  elapsed = time.perf_counter() - s
  return elapsed


if __name__ == '__main__':
  l1 = np.random.randint(0, 1000000, 100000)
  l2 = np.random.randint(0, 1000000, 100000)
  l3 = np.random.randint(0, 1000000, 100000)

  average_sequential = []
  average_async = []
  average_threading = []
  average_multiprocessing = []
  
  # number of iterations
  user_input =  1500

  s = time.perf_counter()
  for i in range(user_input):
    average_sequential.append(sequential_wrapper(l1, l2, l3))
    average_async.append(asyncio.run(async_wrapper(l1, l2, l3)))
    average_threading.append(threading_wrapper(l1, l2, l3))
    average_multiprocessing.append(multiprocessing_wrapper(l1, l2, l3))
  
  print(f"average sequential is {np.mean(average_sequential)}")
  print(f"average async is {np.mean(average_async)}")
  print(f"average threading is {np.mean(average_threading)}")
  print(f"average multiprocessing {np.mean(average_multiprocessing)}")

  total_time = time.perf_counter() - s
  print(f"The total elapsed time since input is: {total_time}")
