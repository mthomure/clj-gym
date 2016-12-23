# clj-gym

Clojure bindings for OpenAI's
[gym-http-api server](https://github.com/openai/gym-http-api/).

## Installation

With Leiningen/Boot:

```
[mthomure/clj-gym "0.1.0-SNAPSHOT"]
```

## Usage

The `clj-gym.core` namespace follows OpenAI's
[API description](https://github.com/openai/gym-http-api#api-specification).

``` clojure
> (require '[clj-gym.core :as g])

> (g/create "CartPole-v0")
"2f7eef5a"

> (g/envs)
{:2f7eef5a "CartPole-v0"}

> (g/reset "2f7eef5a)
{:observation [0.023710455775827657 0.019351021639290936 0.011374305124354243 -0.016630857354495036]}

> (g/step "2f7eef5a" 0)
{:done false, :info {}, :observation [0.024097476208613477 -0.1759321880637575 0.011041687977264342 0.2796189824583605], :reward 1.0}
```

Get an idea of valid actions and observations using:

``` clojure
> (g/action-space "2f7eef5a")
{:n 2, :name "Discrete"}

> (g/observation-space "2f7eef5a")
{:high [4.8 3.4028234663852886E38 0.41887902047863906 3.4028234663852886E38], :low [-4.8 -3.4028234663852886E38 -0.41887902047863906 -3.4028234663852886E38], :name "Box", :shape [4]}
```

Talking to a remote server instance? Use the `*url*` dynamic variable.

``` clojure
> (binding [g/*url* "http://HOST:PORT"] (g/envs))
```

## Running the Server

The server is available from DockerHub:

```
docker run -p 5000:5000 mthomure/openai-gym-server
```

## Rebuilding the Docker Image

```
cd docker/
docker build -t mthomure/openai-gym-server .
docker login
docker push mthomure/openai-gym-server
```
