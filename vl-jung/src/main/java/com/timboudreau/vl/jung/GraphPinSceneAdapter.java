/*
 * Copyright (c) 2018, Tim Boudreau
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.timboudreau.vl.jung;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.api.visual.graph.GraphPinScene;

/**
 *
 * @author Tim Boudreau
 */
final class GraphPinSceneAdapter<N, E, P> extends AbstractGraphProvider<N, E, GraphPinScene<N, E, P>> {

    public GraphPinSceneAdapter(GraphPinScene<N, E, P> scene) {
        super(scene);
    }

    @Override
    public Collection<E> getEdges() {
        return scene.getEdges();
    }

    @Override
    public Collection<N> getNodes() {
        return scene.getNodes();
    }

    public Collection<E> getEdges(N node, boolean input, boolean output) {
        if (!input && !output) {
            throw new IllegalArgumentException("input and output cannot both be false");
        }
        Collection<P> pins = scene.getNodePins(node);
        Set<E> result = new HashSet<>();
        Collection<E> allEdges = scene.getEdges();
        for (E e : allEdges) {
            if (input) {
                if (pins.contains(scene.getEdgeSource(e))) {
                    result.add(e);
                }
            }
            if (output) {
                if (pins.contains(scene.getEdgeTarget(e))) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    public Collection<Object> getDependentObjects(N node) {
        Set<Object> result = new HashSet<>(scene.getNodePins(node));
        result.addAll(getEdges(node, true, true));
        return result;
    }

}
